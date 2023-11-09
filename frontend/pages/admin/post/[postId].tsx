import { getPostByIdWithToken } from '@/api/api-executor'
import PostDto from '@/api/models/PostDto'
import { useAuthContext } from '@/store/auth-context'
import EditPostForm from '@/ui/post/EditPostForm'
import { useRouter } from 'next/router'
import { FC, useEffect, useState } from 'react'
import { toast } from 'react-toastify'

const EditPostPage: FC<{}> = () => {
  const { query } = useRouter()
  const { token } = useAuthContext()
  const [post, setPost] = useState<PostDto>()

  useEffect(() => {
    if (!query.postId || !token) {
      return
    }

    getPostByIdWithToken(token, query.postId.toString()).then((response) => {
      response.id ? setPost(response) : toast.error(response.message)
    })
  }, [query, token])

  return <EditPostForm post={post} />
}

export default EditPostPage
