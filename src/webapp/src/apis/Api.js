import { axiosInstance, axiosInstanceInterceptors } from "../config/axiosInstance";

const api = {
    GET_STORE_DATA_URL: "/api/shopify/store",
};


class Api {
    static async getStoreData(app) {
        axiosInstanceInterceptors(app)
        return axiosInstance.request({
            method: 'get',
            url: api.GET_STORE_DATA_URL
        });
    }
}

export default Api;