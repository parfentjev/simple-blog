import PostsPage from './pages/posts/PostsPage'
import SearchPage from './pages/core/SearchPage'
import LicensePage from './pages/core/LicensePage'
import { ReactNode } from 'react'
import PostPage from './pages/posts/PostPage'
import NotFoundPage from './pages/core/NotFoundPage'

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
    { path: '*', element: <NotFoundPage /> },
]

export default routes