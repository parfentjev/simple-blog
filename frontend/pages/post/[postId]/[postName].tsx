import { getPostById } from '@/api/api-executor'
import Post from '@/api/models/Post'
import { PostComponent } from '@/ui/components/post/PostComponent'
import { useRouter } from 'next/router'
import { FC, useEffect, useState } from 'react'

const PostPage: FC = () => {
  const postId = useRouter().query.postId
  const [post, setPost] = useState<Post>()

  console.log(postId)

  useEffect(() => {
    if (postId) {
      getPostById(postId.toString()).then((response) => setPost(response))
    }
  }, [postId])

  return (post && <PostComponent post={post} />) || <p>loading...</p>
}

export default PostPage
