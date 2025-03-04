import { FC, useEffect, useState } from 'react'
import { useParams } from 'react-router'
import { toast } from 'react-toastify'
import SinglePost from '../../../components/posts/SinglePost'
import { postsApi } from '../../../api/api'
import { PostDto } from '../../../../codegen'
import { setPageTitle } from '../../../utils/title-utils'

const PostPage: FC = () => {
    const { id } = useParams()

    const [post, setPost] = useState<PostDto>()

    useEffect(() => {
        if (id) {
            postsApi()
                .postsPublishedIdGet({ id })
                .then((post) => {
                    setPost(post)
                    setPageTitle(post.title)
                })
                .catch(() => toast.error('Failed to load post.'))
        }
    }, [id])

    if (!post) {
        return <p className="text-center">Post not found!</p>
    }

    return <SinglePost post={post} />
}

export default PostPage
