// Routes.jsx
import React from 'react';
import { Switch, Route, withRouter } from 'react-router';
import { ClientRouter, RoutePropagator } from '@shopify/app-bridge-react';

import LoginSuccess from './pages/LoginSuccess';
import AppLayoutRoute from './pages/AppLayoutRoute';
import Logout from './pages/Logout';
import LoginFailed from './pages/LoginFailed';
import Dashboard from './pages/Dashboard';
import StoreInstall from './pages/StoreInstall';

function Routes(props) {
    const { history, location } = props;

    return (
        <>
            <ClientRouter history={history} />
            <RoutePropagator location={location} />
            <Switch>
                <Route exact path="/">
                </Route>
                <Route exact path="/store_install"
                    component={StoreInstall}>
                </Route>
                <Route
                    skipLogin={true}
                    path="/login-success"
                    component={LoginSuccess}
                />
                <Route path="/login-failed" component={LoginFailed} />

                <AppLayoutRoute
                    path="/logout"
                    component={Logout}
                />
                <AppLayoutRoute
                    path="/dashboard"
                    component={Dashboard}
                />
            </Switch>

        </>
    );
};

export default withRouter(Routes);