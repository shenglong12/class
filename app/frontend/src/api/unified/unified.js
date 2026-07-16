import service from "@/utils/request";

const BASE_API = "/api";

export default {
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
    api(params = {}) {
        const {method_name, param = {}} = params;
        return service({
            url: BASE_API + `/${method_name}`,
            method: "post",
            data: param
        });
    }
}