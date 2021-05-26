import React from "react";
import ReactDOM from "react-dom";

import "@shopify/polaris/dist/styles.css";

import App from "./App";

function WrappedApp() {
    return <App />;
}

ReactDOM.render(<WrappedApp />, document.getElementById("root"));