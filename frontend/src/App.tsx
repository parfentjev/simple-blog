import { FC } from 'react'
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import PostListPage from './ui/pages/PostListPage'
import Layout from './ui/Layout'
import PostPage from './ui/pages/PostPage'

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />,
    children: [
      { index: true, element: <PostListPage /> },
      { path: 'post/:postId', element: <PostPage /> },
      { path: 'post/:postId/:encodedTitle', element: <PostPage /> },
    ],
  },
])

const App: FC = () => {
  return <RouterProvider router={router} />
}

export default App
