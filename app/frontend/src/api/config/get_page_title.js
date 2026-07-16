import page_title_config  from "./page_title_config"

export default function (page_name) {
    if (page_name.includes("/")) {
        let splits = page_name.split("/");
        if (splits.length >= 2) {
            page_name = splits[1] // 取真实的用户名称
        }
    }
    if (page_name in page_title_config) {
        return page_title_config[page_name];
    }
    return null;
}