import PostListPage from './pages/posts/PostListPage'
import SearchPage from './pages/core/SearchPage'
import LicensePage from './pages/core/LicensePage'
import { ReactNode } from 'react'
import PostPage from './pages/posts/PostPage'
import NotFoundPage from './pages/core/NotFoundPage'
import AuthPage from './pages/admin/AuthPage'
import AdminHomePage from './pages/admin/AdminHomePage'
import { ProtectedRoute } from '../store/auth-context'
import PostEditor from './pages/admin/PostEditorPage'
import EditorPostListPage from './pages/admin/EditorPostListPage'
import MediaListPage from './pages/admin/MediaListPage'

interface Route {
    path: string
    element: ReactNode
}

const routes: Route[] = [
    { path: '/', element: <PostListPage /> },
    { path: '/posts/:page', element: <PostListPage /> },
    { path: '/search', element: <SearchPage /> },
    { path: '/license', element: <LicensePage /> },
    { path: '/post/:id', element: <PostPage /> },
    { path: '/post/:id/:title', element: <PostPage /> },
    { path: '/admin/auth', element: <AuthPage /> },
    {
        path: '/admin',
        element: (
            <ProtectedRoute>
                <AdminHomePage />
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
    {
        path: '/admin/posts',
        element: (
            <ProtectedRoute>
                <EditorPostListPage />
            </ProtectedRoute>
        ),
    },
    {
        path: '/admin/media/:page',
        element: (
            <ProtectedRoute>
                <MediaListPage />
            </ProtectedRoute>
        ),
    },
    { path: '*', element: <NotFoundPage /> },
]

export default routes
