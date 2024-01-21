import { FC, useEffect, useState } from 'react'
import { getPostsPublished } from '../../../api/postService'
import { toast } from 'react-toastify'
import { PostPreviewDto } from '../../../api/models/post'
import PostsList from '../../../ui/posts/PostsList'

const PostsPage: FC = () => {
    const [posts, setPosts] = useState<PostPreviewDto[]>()

    useEffect(() => {
        getPostsPublished()
            .then((posts) => setPosts(posts))
            .catch(() => toast.error('Failed to laod posts.'))
    }, [])

    if (!posts) {
        return <p className="text-center">There are no posts yet!</p>
    }

    return <PostsList posts={posts} />
}

export default PostsPage
