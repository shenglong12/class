const BASE_API = "/api";
import service from "@/utils/request";

export default {
    /**
     * 获取当前的经纬度信息
     * @param type      返回的坐标类型，gcj02 返回国测局加密坐标，默认为 wgs84 返回 gps 坐标（高德，百度，Google Maps使用这个坐标）
     * @param altitude  是否返回高度信息
     * @param isHighAccuracy 是否使用高精度定位
     */
    getCurrentLocation(param) {
        if (!param) {
            param = {}
        }
        const {type = 'wgs84', altitude = false, isHighAccuracy = false} = param
        return new Promise((resolve, reject) => {
            uni.getLocation({
                type: type, // 默认使用 'wgs84' 类型，可以根据需求修改为 'gcj02' 等
                success: (res) => {
                    const {latitude, longitude, speed, altitude, horizontalAccuracy} = res;
                    resolve({
                        success: true,
                        latitude, // 纬度，浮点数，范围为-90~90，负数表示南纬
                        longitude, // 经度，浮点数，范围为-180~180，负数表示西经
                        speed, // 速度，浮点数，单位m/s
                        altitude, // 高度，单位 m
                        horizontalAccuracy // 水平精度，单位 m
                    });
                },
                fail: (err) => {
                    console.error('获取位置失败', err);
                    service({
                        url: '/api/amap_ip_location',
                        method: "post",
                        data: {}
                    }).then((res) => {
                        const [point1, point2] = res.data.rectangle.split(';');
                        const [lon1, lat1] = point1.split(',').map(Number);
                        const [lon2, lat2] = point2.split(',').map(Number);

                        // 计算中心点经纬度
                        const longitude = (lon1 + lon2) / 2;
                        const latitude = (lat1 + lat2) / 2;

                        // 构建新格式
                        resolve({
                            success: true,
                            latitude: latitude,       // 纬度
                            longitude: longitude,     // 经度
                            speed: 0,                 // 默认速度0
                            altitude: 0,              // 默认海拔0
                            horizontalAccuracy: 1000  // 默认水平精度1000米(1公里)
                        });
                    }).catch((res) => {
                        reject(err); // 错误信息传递给调用者
                    });
                }
            });
        });
    },


    /**
     * 根据经纬度获取详细的地址信息
     * @param param
     */
    getAddressByLocation(param = {}) {
        // 参数验证
        if (!param.latitude || !param.longitude) {
            return Promise.reject(new Error('Missing latitude or longitude'));
        }

        return service({
            url: BASE_API + "/geocoder",
            method: "post",
            data: {
                location: `${param.latitude},${param.longitude}`
            }
        }).then(res => {
            let result = res.data
            result.success = true
            return result
        }); // 直接返回需要的数据

    },
    getLocationByAddress(param){
        if (!param.address ) {
            return Promise.reject(new Error('Missing address'));
        }
        return service({
            url: BASE_API + "/geoaddress",
            method: "post",
            data: {
                address: `${param.address}`
            }
        }).then(res => {
            let result = res.data
            result.success = true
            // console.log( result)
            if (result.hasOwnProperty('status')) {
                // console.log(result)
                if (result.status !== 0) {
                    return Promise.reject(new Error(result.message));
                }
            }
            result.result.longitude = result.result.location.lng;
            result.result.latitude = result.result.location.lat;
            return result
        }); // 直接返回需要的数据
    },
    /**
     * 打开地图选择一个地址返回
     * @param param
     * @returns {Promise<unknown>}
     */
    chooseLocation(param) {
        return new Promise((resolve, reject) => {
            // 小程序平台可直接使用 chooseLocation
            uni.chooseLocation({
                success: res => {
                    res.success = true;
                    resolve(res);
                },
                fail: function (err) {
                    reject(err);
                }
            });
        });
    }
}