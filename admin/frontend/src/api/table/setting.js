import service from "@/utils/request";

export default {
    login() {
        return service({
            url: "/system/setting/login",
            method: "get",
        });
    }
};