import React from 'react'
import { clearShopOrigin } from '../utils/Utils';

export default function Logout() {
    clearShopOrigin();
    window.location.href = "/"
}