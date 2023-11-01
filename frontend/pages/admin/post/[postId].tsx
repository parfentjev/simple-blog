import { getPostByIdWithToken } from '@/api/api-executor'
import PostDto from '@/api/models/PostDto'
import { useAuthContext } from '@/store/auth-context'
import EditPostForm from '@/ui/post/EditPostForm'
import { useRouter } from 'next/router'
import { FC, useEffect, useState } from 'react'

const EditPostPage: FC<{}> = () => {
  const { query } = useRouter()
  const { token } = useAuthContext()
  const [post, setPost] = useState<PostDto>()

  useEffect(() => {
    if (!query.postId || !token) {
      return
    }

    getPostByIdWithToken(token, query.postId.toString()).then((response) => {
      if (response.id) {
        setPost(response)
      } else {
        // to do
      }
    })
  }, [query, token])

  return <EditPostForm post={post} />
}

export default EditPostPage
