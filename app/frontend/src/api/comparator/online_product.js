import service from "@/utils/request";

const BASE_API = "/api";

export default {
    query_info(params = {}) {
        return service({
            url: BASE_API + `/bijia_spu_search`,
            method: "post",
            data: params
        });
    },
    lowest_price(params = {}) {
        return service({
            url: BASE_API + `/bijia_spu_goods_search`,
            method: "post",
            data: params
        });
    },
}