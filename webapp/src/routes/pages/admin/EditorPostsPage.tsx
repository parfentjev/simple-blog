import { FC, useEffect, useState } from 'react'
import { PostPreviewDto } from '../../../api/codegen'
import { postsApi } from '../../../api/api'
import { useAuthContext } from '../../../store/auth-context'
import { toast } from 'react-toastify'
import EditorPostsList from '../../../ui/admin/EditorPostsList'

const EditorPostsPage: FC = () => {
    const { token } = useAuthContext()
    const [posts, setPosts] = useState<PostPreviewDto[]>()

    useEffect(() => {
        postsApi(token)
            .postsEditorGet()
            .then((posts) => setPosts(posts))
            .catch(() => toast.error('Failed to laod posts.'))
    }, [token])

    if (!posts) {
        return <p className="text-center">There are no posts yet!</p>
    }

    return <EditorPostsList posts={posts} />
}

export default EditorPostsPage
