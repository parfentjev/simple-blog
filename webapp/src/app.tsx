import { FC } from 'react'
import Layout from './ui/core/Layout'
import {
    BrowserRouter,
    Route,
    RouterProvider,
    Routes,
    createBrowserRouter,
} from 'react-router-dom'
import routes from './routes/routes'
import './index.css'

const App: FC = () => {
    return (
        <BrowserRouter>
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
        </BrowserRouter>
    )
}

export default App
