export default function formatError(err) {
    if (!err) return "Unknown Error";

    // 如果是 Error 对象
    if (err instanceof Error) {
        return `${err.stack}`;
    }

    // 如果是 Promise rejection，可能有 reason
    if (err.reason) {
        if (err.reason instanceof Error) {
            return `${err.reason.name}: ${err.reason.message}\n${err.reason.stack}`;
        }

        if (typeof err.reason === "string") {
            return `${err.reason}`;
        }

        if (typeof err.reason === "object") {
            try {
                return "Promise rejection: " + JSON.stringify(err.reason, Object.getOwnPropertyNames(err.reason), 2);
            } catch {
                return String(err.reason);
            }
        }

        return String(err.reason);
    }

    // 如果是对象（可能是小程序抛的）
    if (typeof err === "object") {
        try {
            return JSON.stringify(err, Object.getOwnPropertyNames(err), 2);
        } catch (e) {
            return String(err);
        }
    }

    // 兜底（字符串或其他类型）
    return String(err);
}