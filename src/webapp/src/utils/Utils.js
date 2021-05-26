export function getShopOrigin() {
    return localStorage.getItem('shopOrigin');
};

export function clearShopOrigin() {
    return localStorage.removeItem('shopOrigin');
};

export function setShopOrigin(shopOrigin) {
    return localStorage.setItem('shopOrigin', shopOrigin);
};