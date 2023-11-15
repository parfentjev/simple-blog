import { ProtectedRoute } from '@/store/auth-context'
import EditPostForm from '@/ui/post/EditPostForm'
import { FC } from 'react'

const CreatePostPage: FC = () => {
  return (
    <ProtectedRoute>
      <EditPostForm />
    </ProtectedRoute>
  )
}

export default CreatePostPage
