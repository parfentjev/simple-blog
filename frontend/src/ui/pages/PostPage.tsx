import { FC, useEffect, useState } from 'react'
import { getPostById } from '../../api/api-executor'
import Post from '../../api/models/Post'
import { PostComponent } from '../components/post/PostComponent'
import { useParams } from 'react-router-dom'

const PostPage: FC = () => {
  const postId = useParams().postId
  const [post, setPost] = useState<Post>()

  useEffect(() => {
    if (postId) {
      getPostById(postId).then((response) => setPost(response))
    }
  }, [postId])

  return (post && <PostComponent post={post} />) || <p>loading...</p>
}

export default PostPage
