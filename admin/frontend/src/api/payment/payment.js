import request from '@/utils/request';

const BASE_API = "/generalOrder";

import {saveAs} from 'file-saver'
import {ElLoading, ElMessage} from "element-plus";
import errorCode from "@/utils/errorCode.js";
import {blobValidate} from "@/utils/ruoyi.js";
let downloadLoadingInstance;

export default {
    refund(id) {
        return request({
            url: BASE_API + "/refund",
            method: "post",
            data: {
                id:id
            }
        });
    }

};
