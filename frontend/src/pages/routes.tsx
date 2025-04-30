import PostListPage from './posts/PostListPage'
import SearchPage from './core/SearchPage'
import LicensePage from './core/LicensePage'
import { ReactNode } from 'react'
import PostPage from './posts/PostPage'
import NotFoundPage from './core/NotFoundPage'
import AuthPage from './admin/AuthPage'
import AdminHomePage from './admin/AdminHomePage'
import { ProtectedRoute } from '../store/auth-context'
import PostEditor from './admin/PostEditorPage'
import EditorPostListPage from './admin/EditorPostListPage'
import MediaListPage from './admin/MediaListPage'
import BannerPage from './core/BannerPage'

interface Route {
  path: string
  element: ReactNode
}

const routes: Route[] = [
  { path: '/', element: <PostListPage /> },
  { path: '/posts/:page', element: <PostListPage /> },
  { path: '/search', element: <SearchPage /> },
  { path: '/license', element: <LicensePage /> },
  { path: '/banner', element: <BannerPage /> },
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
