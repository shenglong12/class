import service from "@/utils/request";

export default {
    signInWithPassword({user_name, phone, email, password} = {}) {
        const account = user_name || phone || email;
        if (!account) {
            throw new Error("必须提供 user_name、phone 或 email 之一");
        }
        if (!password) {
            throw new Error("必须提供 password");
        }
        return service({
            url: "/login/passwd",
            method: "post",
            data: {
                phone: account,
                password: password
            }
        }).then((res) => {
            uni.setStorage({key: "h5_token", data: res.data})
            return {success: true, code: 0, message: "ok"};
        });
    }
}