import axios from 'axios';
import { getSessionToken } from "@shopify/app-bridge-utils";

import { ApiUrl } from "../Constants";
import { useAppBridge } from '@shopify/app-bridge-react';
import isEmpty from 'is-empty';

export const axiosInstance = axios.create({
    baseURL: `${ApiUrl}`,
    headers: {
        'Content-Type': 'application/json'
    }
});



const errorHandler = (error) => {
    if (error && error.response && error.response.status && (error.response.status === 401 || error.response.status === 403)) {
        window.location.href = "/login-failed";
    }
}

axiosInstance.interceptors.response.use(
    response => { return response },
    error => {
        errorHandler(error);
        return Promise.reject(error);
    }
);

axiosInstance.interceptors.request.use(function (config) {
    if (!isEmpty(window.app)) {
        return getSessionToken(window.app) // requires an App Bridge instance
            .then((token) => {
                config.baseURL = `${ApiUrl}`,
                    // append your request headers with an authenticated token
                    config.headers["Authorization"] = `Bearer ${token}`;
                return config;
            });
    }
});

// intercept all requests on this axios instance



