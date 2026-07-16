import service from "@/utils/request";

const BASE_API = "/api";

export default {
    queryTickets(params = {}) {
        const {from, to, date} = params
        return service({
            url: BASE_API + "/queryTickets",
            method: "post",
            data: {
                from: from,
                to: to,
                date: date,
            }
        });
    },
    queryFlight(params = {}) {
        const {from, to, date} = params
        return service({
            url: BASE_API + "/queryFlight",
            method: "post",
            data: {
                from: from,
                to: to,
                date: date,
            }
        });
    }
}