export function getShopOrigin() {
    return localStorage.getItem('shopOrigin');
};

export function clearShopOrigin() {
    return localStorage.removeItem('shopOrigin');
};

export function setShopOrigin(shopOrigin) {
    return localStorage.setItem('shopOrigin', shopOrigin);
};

export const redirectLogin = async (app) => {

}

export const setStoreDetails = (storeDetail) => {
    localStorage.setItem("storeDetail", JSON.stringify(storeDetail));
}

export const getStoreDetails = () => {
    return localStorage.getItem("storeDetail");
}

