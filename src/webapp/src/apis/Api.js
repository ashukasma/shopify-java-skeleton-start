import { axiosInstance, axiosInstanceInterceptors } from "../config/axiosInstance";

const api = {
    GET_STORE_DATA_URL: "/api/shopify/store",
    GET_BILLING_URL: "/api/shopify/billing",
};


class Api {
    static async getStoreData() {
        return axiosInstance.request({
            method: 'get',
            url: api.GET_STORE_DATA_URL
        });
    }

    static async getBillingUrl() {
        return axiosInstance.request({
            method: 'get',
            url: api.GET_BILLING_URL
        });
    }
}

export default Api;