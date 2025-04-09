import { FC } from 'react'
import Layout from './components/core/Layout'
import { BrowserRouter, Route, Routes } from 'react-router'
import routes from './pages/routes'
import { AuthContextProvider } from './store/auth-context'

const App: FC = () => {
    return (
        <BrowserRouter>
            <AuthContextProvider>
                <Layout>
                    <Routes>
                        {routes.map((route) => (
                            <Route
                                key={route.path}
                                path={route.path}
                                element={route.element}
                            />
                        ))}
                    </Routes>
                </Layout>
            </AuthContextProvider>
        </BrowserRouter>
    )
}

export default App
