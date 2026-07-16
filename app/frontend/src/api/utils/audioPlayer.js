// src/utils/audioPlayer.js
export default class AudioPlayer {
    constructor({ src, autoplay = false, loop = false }) {
        this.isWeapp = typeof wx !== 'undefined' && wx.createInnerAudioContext;
        this.events = {};
        this._isDestroyed = false;
        this._currentSrc = src;

        // ================= 微信小程序端 =================
        // #ifdef MP-WEIXIN
        this._createWeappAudioContext(src, autoplay, loop);
        // #endif

        // ================= H5 端 =================
        // #ifdef H5
        this._createH5AudioContext(src, autoplay, loop);
        // #endif
    }

    // ================ 私有方法 ================
    _createWeappAudioContext(src, autoplay, loop) {
        this._destroyWeappAudioContext();

        this.audio = wx.createInnerAudioContext({
            useWebAudioImplement: true
        });

        if (this.audio) {
            this.audio.src = src;
            this.audio.loop = loop;
            this.audio.autoplay = autoplay;
            this._bindWeappEvents(autoplay);
        }
    }

    _destroyWeappAudioContext() {
        if (this.audio) {
            try {
                if (this.audio.stop) this.audio.stop();
                this._unbindAllEvents();

                // ✅ 清空 src 保证释放资源
                try { this.audio.src = ''; } catch (e) {}

                if (this.audio.destroy) this.audio.destroy();
            } catch (e) {
                console.warn('销毁音频实例时出错:', e);
            }
            this.audio = null;
        }
    }

    _bindWeappEvents(autoplay) {
        this._unbindAllEvents();
        if (!this.audio) return;

        if (this.audio.onPlay) {
            this.audio.onPlay(() => {
                if (this._isDestroyed) return;
                this.emit('play');
            });
        }

        if (this.audio.onPause) {
            this.audio.onPause(() => {
                if (this._isDestroyed) return;
                this.emit('pause');
            });
        }

        if (this.audio.onEnded) {
            this.audio.onEnded(() => {
                if (this._isDestroyed) return;
                this.emit('ended');

                if (this.audio && this.audio.loop) {
                    this.seek(0);
                    this.play();
                }
                // ❌ 不做其他处理，交给 play() 逻辑重建实例
            });
        }

        if (this.audio.onCanplay) {
            this.audio.onCanplay(() => {
                if (this._isDestroyed) return;
                this._ensureDuration(autoplay);
            });
        }

        if (this.audio.onTimeUpdate) {
            this.audio.onTimeUpdate(() => {
                if (this._isDestroyed) return;
                this.emit('timeupdate', this.getCurrentTime());
                const dur = this.getDuration();
                if (dur > 0) this.emit('durationchange', dur);
            });
        }

        if (this.audio.onSeeked) {
            this.audio.onSeeked(() => {
                if (this._isDestroyed) return;
                this.emit('seeked', this.getCurrentTime());
            });
        }

        if (this.audio.onError) {
            this.audio.onError((err) => {
                console.error('[AudioPlayer] 小程序播放错误:', err);
                this.emit('error', err);
            });
        }
    }

    _createH5AudioContext(src, autoplay, loop) {
        this.audio = new Audio(src);
        if (this.audio) {
            this.audio.loop = loop;
            this.audio.autoplay = autoplay;
            this._bindH5Events(autoplay);
        }
    }

    _bindH5Events(autoplay) {
        if (!this.audio) return;

        const forceDecode = () => {
            if (this.audio && this.audio.play) {
                this.audio.play().then(() => {
                    if (this.audio && this.audio.pause) {
                        this.audio.pause();
                    }
                }).catch(() => {});
            }
        };

        this.audio.addEventListener('loadedmetadata', () => {
            const dur = this.getDuration();
            if (dur > 0 && isFinite(dur)) {
                this.emit('durationchange', dur);
            } else {
                forceDecode();
            }
        });

        this.audio.addEventListener('play', () => this.emit('play'));
        this.audio.addEventListener('pause', () => this.emit('pause'));
        this.audio.addEventListener('ended', () => {
            this.emit('ended');
            if (this.audio && this.audio.loop) {
                this.audio.currentTime = 0;
                this.play().catch(err => console.warn('循环播放失败:', err));
            }
        });
        this.audio.addEventListener('timeupdate', () => {
            const dur = this.getDuration();
            if (dur > 0 && isFinite(dur)) {
                this.emit('durationchange', dur);
            }
            this.emit('timeupdate', this.getCurrentTime());
        });
        this.audio.addEventListener('seeked', () => {
            this.emit('seeked', this.getCurrentTime());
        });
        this.audio.addEventListener('error', (err) => this.emit('error', err));
    }

    _unbindAllEvents() {
        if (!this.audio) return;
        const events = ['Play', 'Pause', 'Ended', 'Canplay', 'TimeUpdate', 'Seeked', 'Error'];
        events.forEach(event => {
            const method = 'off' + event;
            if (this.audio[method]) {
                this.audio[method](() => {});
            }
        });
    }

    _ensureDuration(autoplay) {
        if (this._isDestroyed || !this.audio) return;

        if (this.audio.play) this.audio.play();
        let tried = 0;
        const tryGetDuration = () => {
            if (this._isDestroyed || !this.audio) return;
            tried++;
            const dur = this.getDuration();
            if (dur > 0) {
                this.emit('durationchange', dur);
                if (!autoplay && this.audio.pause) this.audio.pause();
            } else if (tried < 10) {
                setTimeout(tryGetDuration, 300);
            }
        };
        tryGetDuration();
    }

    // ================ 公共方法 ================
    play() {
        if (this._isDestroyed) return Promise.reject(new Error('AudioPlayer已被销毁'));

        if (this.isWeapp) {
            return new Promise((resolve, reject) => {
                if (!this._currentSrc) {
                    reject(new Error('音频源未设置'));
                    return;
                }

                // ✅ 如果已播放结束，销毁并重建实例
                if (this.audio && this.getDuration() > 0 && this.getCurrentTime() >= this.getDuration()) {
                    const loop = this.audio.loop;
                    this._destroyWeappAudioContext();
                    this._createWeappAudioContext(this._currentSrc, false, loop);
                }

                const tryPlay = () => {
                    if (this._isDestroyed) {
                        reject(new Error('AudioPlayer已被销毁'));
                        return;
                    }

                    if (this.audio && this.audio.src) {
                        if (this.audio.play) {
                            this.audio.play();
                            resolve();
                        } else {
                            reject(new Error('播放失败：audio.play 不存在'));
                        }
                    } else {
                        setTimeout(tryPlay, 100);
                    }
                };

                tryPlay();
            });
        } else {
            if (this.audio && this.audio.play) {
                return this.audio.play().catch(err => {
                    console.warn('播放失败：', err);
                    this.emit('error', err);
                    throw err;
                });
            }
            return Promise.reject(new Error('音频播放器不可用'));
        }
    }

    pause() {
        if (this._isDestroyed || !this.audio || !this.audio.pause) return;
        this.audio.pause();
    }

    setSrc(src, autoplay = true) {
        if (this._isDestroyed || !src) return;
        this._currentSrc = src;

        if (this.isWeapp) {
            this._createWeappAudioContext(src, autoplay, this.audio ? this.audio.loop : false);
        } else {
            if (this.audio) {
                this.audio.src = src;
                if (autoplay) this.play();
            }
        }
    }

    setVolume(volume) {
        if (this._isDestroyed || !this.audio) return;
        volume = Math.max(0, Math.min(1, volume));
        if (this.audio && typeof this.audio.volume !== 'undefined') {
            this.audio.volume = volume;
        }
    }

    setLoop(loop) {
        if (this._isDestroyed || !this.audio) return;
        this.audio.loop = loop;
    }

    seek(seconds) {
        if (this._isDestroyed || !this.audio) return;
        if (this.audio.seek) {
            this.audio.seek(seconds);
        } else if (!isNaN(this.audio.duration)) {
            this.audio.currentTime = Math.min(this.audio.duration, Math.max(0, seconds));
            this.emit('seeked', this.audio.currentTime);
        }
    }

    getCurrentTime() {
        if (this._isDestroyed || !this.audio) return 0;
        return this.audio.currentTime || 0;
    }

    getDuration() {
        if (this._isDestroyed || !this.audio) return 0;
        return this.audio.duration || 0;
    }

    on(event, callback) {
        if (this._isDestroyed) return;
        if (!this.events[event]) this.events[event] = [];
        this.events[event].push(callback);
    }

    emit(event, ...args) {
        if (this._isDestroyed || !this.events[event]) return;
        for (let cb of this.events[event]) cb(...args);
    }

    destroy() {
        if (this._isDestroyed) return;
        this.pause();
        this._isDestroyed = true;
        if (this.isWeapp) {
            this._destroyWeappAudioContext();
        } else if (this.audio) {
            this.audio.src = '';
            this.audio = null;
        }
        this.events = {};
    }
}