import PostsPage from './pages/posts/PostsPage'
import SearchPage from './pages/core/SearchPage'
import LicensePage from './pages/core/LicensePage'
import { ReactNode } from 'react'
import PostPage from './pages/posts/PostPage'
import NotFoundPage from './pages/core/NotFoundPage'
import AuthPage from './pages/admin/AuthPage'
import AdminHome from './pages/admin/AdminHome'
import { ProtectedRoute } from '../store/auth-context'
import PostEditor from './pages/admin/PostEditor'

interface Route {
    path: string
    element: ReactNode
}

const routes: Route[] = [
    { path: '/', element: <PostsPage /> },
    { path: '/search', element: <SearchPage /> },
    { path: '/license', element: <LicensePage /> },
    { path: '/post/:id', element: <PostPage /> },
    { path: '/post/:id/:title', element: <PostPage /> },
    { path: '/admin/auth', element: <AuthPage /> },
    {
        path: '/admin',
        element: (
            <ProtectedRoute>
                <AdminHome />
            </ProtectedRoute>
        ),
    },
    {
        path: '/admin/post',
        element: (
            <ProtectedRoute>
                <PostEditor />
            </ProtectedRoute>
        ),
    },
    {
        path: '/admin/post/:id',
        element: (
            <ProtectedRoute>
                <PostEditor />
            </ProtectedRoute>
        ),
    },
    { path: '*', element: <NotFoundPage /> },
]

export default routes
