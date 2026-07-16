<template>
    <view class="w-full flex items-center gap-2 mt-2">
        <text class="text-xs text-neutral-400 min-w-[36px]">
            {{ audioCurrentTimeFormatted }}
        </text>
        <view class="flex-1 mx-2 relative h-3 rounded-full overflow-hidden progress-bar-container" :class="barColor"
            ref="progressBarRef">
            <view class="absolute left-0 top-0 h-full transition-all" :class="handleColor"
                :style="{ width: progressPercent + '%' }"></view>
            <view
                class="absolute -top-1 w-5 h-5 rounded-full border border-white shadow-md transform -translate-x-1/2 cursor-pointer"
                :class="handleColor" :style="{ left: progressPercent + '%' }" @touchstart.prevent="onTouchStart"
                @touchmove.prevent="onTouchMove" @touchend.prevent="onTouchEnd" @mousedown.prevent="onMouseDown"></view>
        </view>
        <text class="text-xs text-neutral-400 min-w-[36px]">
            {{ audioDurationFormatted }}
        </text>
    </view>
</template>

<script setup>
import { ref, computed, onMounted, nextTick, getCurrentInstance } from 'vue';

const props = defineProps({
    currentTime: Number,
    duration: Number,
    barColor: {
        type: String,
        default: 'bg-neutral-200'
    },
    handleColor: {
        type: String,
        default: 'bg-yellow-400'
    }
});

const emit = defineEmits(['seek']);

const progressBarRef = ref(null);
let progressBarRect = ref(null);

const getProgressBarRect = () => {
    return new Promise((resolve) => {
        const query = uni.createSelectorQuery().in(getCurrentInstance());
        query.select('.progress-bar-container').boundingClientRect(data => {
            progressBarRect.value = data;
            resolve(data);
        }).exec();
    });
};

onMounted(() => {
    getProgressBarRect();
});

const progressPercent = computed(() => {
    return props.duration > 0 ? (props.currentTime / props.duration) * 100 : 0;
});

const audioCurrentTimeFormatted = computed(() => formatTime(props.currentTime));
const audioDurationFormatted = computed(() => formatTime(props.duration));

function formatTime(sec) {
    sec = Math.floor(sec || 0);
    const m = Math.floor(sec / 60);
    const s = sec % 60;
    return `${m.toString().padStart(2, '0')}:${s.toString().padStart(2, '0')}`;
}

const seeking = ref(false);
const seekPercent = ref(0);

async function onMouseDown(e) {
    if (!progressBarRect.value) {
        await getProgressBarRect();
    }
    seeking.value = true;
    updateSeekPercent(e);

    window.addEventListener('mousemove', onMouseMove);
    window.addEventListener('mouseup', onMouseUp);
}

function onMouseMove(e) {
    if (!seeking.value) return;
    updateSeekPercent(e);
}

function onMouseUp(e) {
    if (!seeking.value) return;
    seeking.value = false;
    updateSeekPercent(e);
    seekToPosition();
    window.removeEventListener('mousemove', onMouseMove);
    window.removeEventListener('mouseup', onMouseUp);
}

async function onTouchStart(e) {
    if (!progressBarRect.value) {
        await getProgressBarRect();
    }
    seeking.value = true;
    updateSeekPercent(e);
}

function onTouchMove(e) {
    if (!seeking.value) return;
    updateSeekPercent(e);
}

function onTouchEnd(e) {
    if (!seeking.value) return;
    seeking.value = false;
    updateSeekPercent(e);
    seekToPosition();
}

function updateSeekPercent(e) {
    let clientX;
    if (e.type.startsWith('touch')) {
        const touch = e.touches?.[0] || e.changedTouches?.[0];
        if (!touch) return;
        clientX = touch.clientX;
    } else {
        clientX = e.clientX;
    }

    if (!progressBarRect.value) return;
    const x = clientX - progressBarRect.value.left;
    let percent = (x / progressBarRect.value.width) * 100;
    percent = Math.max(0, Math.min(100, percent));
    seekPercent.value = percent;
}

function seekToPosition() {
    const newTime = (seekPercent.value / 100) * props.duration;
    emit('seek', newTime);
}
</script>

<style scoped>
.cursor-pointer {
    cursor: pointer;
}
</style>