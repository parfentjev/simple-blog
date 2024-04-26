import { FC, useEffect, useState } from 'react'
import { toast } from 'react-toastify'
import PostsList from '../../../ui/posts/PostsList'
import { PagePostDto } from '../../../api/codegen'
import { postsApi } from '../../../api/api'

const PostsPage: FC = () => {
    const [page, setPage] = useState<PagePostDto>()

    useEffect(() => {
        postsApi()
            .postsPublishedGet()
            .then((page) => setPage(page))
            .catch(() => toast.error('Failed to laod posts.'))
    }, [])

    if (!page || page.items.length === 0) {
        return <p className="text-center">There are no posts yet!</p>
    }

    return <PostsList posts={page.items} />
}

export default PostsPage
