import { FC, useEffect, useState } from 'react'
import { toast } from 'react-toastify'
import PostsList from '../../../ui/posts/PostsList'
import { PostPreviewDto } from '../../../api/codegen'
import { postsApi } from '../../../api/api'

const PostsPage: FC = () => {
    const [posts, setPosts] = useState<PostPreviewDto[]>()

    useEffect(() => {
        postsApi()
            .postsPublishedGet()
            .then((posts) => setPosts(posts))
            .catch(() => toast.error('Failed to laod posts.'))
    }, [])

    if (!posts) {
        return <p className="text-center">There are no posts yet!</p>
    }

    return <PostsList posts={posts} />
}

export default PostsPage
