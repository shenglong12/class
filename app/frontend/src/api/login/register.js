import service from "@/utils/request";

export default {
    register(params = {}) {
        const {table_name, param = {}} = params;
        return service({
            url: `/login/register?table=${table_name}`,
            method: "post",
            data: param
        });
    }
}