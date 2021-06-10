import { ApiUrl, shopifyApiKey } from "../Constants";
import { getShopOrigin } from "../utils/Utils";

let queryParams = new URLSearchParams(window.location.search)
const shop = queryParams.get('shop');
if (!shop) {
    if (!window.location.href.indexOf('login-failed')) {
        window.location.href = `/login-failed`;
    }
}

const shopOrigin = getShopOrigin();
export const shopConfig = { apiKey: shopifyApiKey, host: shopOrigin, forceRedirect: true };