import service from "@/utils/request";

const BASE_API = "/api";

export default {
    open_baidu_nav(params = {}) {
        const {latitude, longitude, address} = params;
        // #ifdef H5 ||  APP-PLUS || APP-HARMONY
        if (this.isWeChat()) {
            window.location.href = `https://map.baidu.com/poi/${encodeURIComponent(address)}/@${longitude},${latitude},19z`;
            return
        }
        uni.getSystemInfo({
            success: (res) => {
                if (res.platform === "android" || (res.system && res.system.toLowerCase().includes("harmony"))) {
                    window.location.href = "baidumap://map/marker?location=" + latitude + "," + longitude + "&title=" + address + "&content=" + address + "&traffic=on";
                } else {
                    window.location.href = "https://api.map.baidu.com/marker?location=" + latitude + "," + longitude + "&title=" + address + "&content=地点&output=html&src=webapp.baidu.openAPIdemo";
                }
            }
        })
        // #endif
        // #ifdef MP
        uni.openLocation({
            latitude: latitude,
            longitude: longitude,
            name: address,
            address: address,
            success: () => {
                console.log("导航已打开")
            }
        })
        // #endif
    },
    open_amap_nav(params = {}) {
        const {latitude, longitude, address} = params;
        // #ifdef H5 ||  APP-PLUS || APP-HARMONY
        if (this.isWeChat()) {
            window.location.href = `https://uri.amap.com/marker?position=${longitude},${latitude}&name=${encodeURIComponent(address)}`;
            return
        }
        uni.getSystemInfo({
            success: (res) => {
                if (res.platform === "android" || (res.system && res.system.toLowerCase().includes("harmony"))) {
                    window.location.href = "androidamap://viewMap?sourceApplication=appname&poiname=" + address + "&lat=" + latitude + "&lon=" + longitude + "&dev=0";
                } else if (res.platform === "ios") {
                    window.location.href = "iosamap://viewMap?sourceApplication=appname&poiname=" + address + "&lat=" + latitude + "&lon=" + longitude + "&dev=0";
                } else {
                    window.location.href = "https://uri.amap.com/marker?position=" + longitude + "," + latitude + "&name=" + address;
                }
            }
        });
        // #endif
        // #ifdef MP
        uni.openLocation({
            latitude: latitude,
            longitude: longitude,
            name: address,
            address: address,
            success: () => {
                console.log("导航已打开")
            }
        });
        // #endif
    },
    open_qqmap_nav(params = {}) {
        const {latitude, longitude, address} = params;
        // #ifdef H5 ||  APP-PLUS || APP-HARMONY
        if (this.isWeChat()) {
            window.location.href = `https://apis.map.qq.com/uri/v1/marker?marker=coord:${latitude},${longitude};title:${encodeURIComponent(address)};addr:${encodeURIComponent(address)}`;
            return
        }
        uni.getSystemInfo({
            success: (res) => {
                if (res.platform === "android" || (res.system && res.system.toLowerCase().includes("harmony"))) {
                    window.location.href = "qqmap://map/marker?marker=coord:" + latitude + "," + longitude + ";title:" + address + ";addr:" + address + "&referer=key";
                } else {
                    window.location.href = `https://apis.map.qq.com/uri/v1/marker?marker=coord:(${latitude},${longitude})&title:${searchText.value}&addr:${searchText.value}`
                }
            }
        });
        // #endif
        // #ifdef MP
        uni.openLocation({
            latitude: latitude,
            longitude: longitude,
            name: address,
            address: address,
            success: () => {
                console.log("导航已打开")
            }
        });
        // #endif
    },
    decodePolyline(polyline) {
        for (let i = 2; i < polyline.length; i++) {
            polyline[i] = polyline[i - 2] + polyline[i] / 1000000
        }
        return polyline;
    },
    driving(params = {}) {
        return service({
            url: BASE_API + `/driving`,
            method: "post",
            data: params
        }).then((res) => {
            const {routes} = res.data.result;
            const paths = routes.map(route => {
                const polyline = this.decodePolyline([...route.polyline]);
                const steps = route.steps.map(step => {
                    const {polyline_idx: [start, end]} = step;
                    const polylines = [];
                    for (let i = start; i <= end; i += 2) {
                        polylines.push({
                            latitude: polyline[i],
                            longitude: polyline[i + 1]
                        });
                    }
                    return {...step, polylines};
                });
                return {
                    distance: route.distance,
                    duration: route.duration,
                    steps
                };
            });
            return {success: true, data: {paths}};
        });
    },
    searchNearby(params) {
        if (!params || !params.latitude || !params.longitude) {
            throw new Error('必须提供经纬度参数');
        }
        const {latitude, longitude, radius = 1000, keyword} = params;

        return service({
            url: BASE_API + `/mapsearch`,
            method: "post",
            data: {
                boundary: `nearby(${latitude},${longitude},${radius})`,
                ...(keyword && {keyword})
            }
        }).then((res) => {
            const {data} = res.data;
            const temps = data.map(d => {
                return {...d, latitude: d.location.lat, longitude: d.location.lng}
            });
            return {success: true, data: {data: temps}};
        });

    },
    amap_geo(params = {}) {
        return service({
            url: BASE_API + `/amap_geo`,
            method: "post",
            data: params
        });
    },
    amap_regeocode(params = {}) {
        return service({
            url: BASE_API + `/amap_regeocode`,
            method: "post",
            data: params
        });
    },
    amap_direction_driving(params = {}) {
        return service({
            url: BASE_API + `/amap_direction_driving`,
            method: "post",
            data: params
        });
    },
    amap_ip_location(params = {}) {
        return service({
            url: BASE_API + `/amap_ip_location`,
            method: "post",
            data: params
        });
    },
    isWeChat() {
        return /MicroMessenger/i.test(window.navigator.userAgent);
    }
}