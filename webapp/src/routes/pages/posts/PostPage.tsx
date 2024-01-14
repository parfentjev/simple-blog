import { FC, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { PostDto } from '../../../api/models/post'
import { getPost } from '../../../api/postService'
import { toast } from 'react-toastify'
import SinglePost from '../../../ui/posts/SinglePost'

const PostPage: FC = () => {
    const { id } = useParams()

    const [post, setPost] = useState<PostDto>()

    useEffect(() => {
        if (id) {
            getPost(id)
                .then((post) => setPost(post))
                .catch(() => toast.error('Failed to load post.'))
        }
    }, [])

    if (!post) {
        return <p className="text-center">Post not found!</p>
    }

    return <SinglePost post={post} />
}

export default PostPage
