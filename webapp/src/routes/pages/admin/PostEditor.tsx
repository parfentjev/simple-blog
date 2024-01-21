import { ChangeEvent, FC, FormEvent, useEffect, useState } from 'react'
import { PostDto } from '../../../api/models/post'
import { useAuthContext } from '../../../store/auth-context'
import { toast } from 'react-toastify'
import { useParams } from 'react-router-dom'
import {
    getPostsEditorById,
    postPostsEditor,
    putPostsEditorById,
} from '../../../api/postService'

const PostEditor: FC = () => {
    const { id } = useParams()
    const { token } = useAuthContext()

    const [postState, setPostState] = useState<PostDto>({
        id: '',
        title: '',
        summary: '',
        text: '',
        date: new Date().toJSON(),
        visible: false,
    })

    useEffect(() => {
        if (!id || !token) {
            return
        }

        getPostsEditorById(token, id)
            .then((post) => setPostState(post))
            .catch(() => toast.error('Failed to load post.'))
    }, [id, token])

    const handleTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
        setPostState((postDto) => {
            return { ...postDto, title: event.target.value }
        })
    }

    const handleSummaryChange = (event: ChangeEvent<HTMLTextAreaElement>) => {
        setPostState((postDto) => {
            return { ...postDto, summary: event.target.value }
        })
    }

    const handleTextChange = (event: ChangeEvent<HTMLTextAreaElement>) => {
        setPostState((postDto) => {
            return { ...postDto, text: event.target.value }
        })
    }

    const handleVisibleChange = (event: ChangeEvent<HTMLInputElement>) => {
        setPostState((postDto) => {
            return { ...postDto, visible: event.target.checked }
        })
    }

    const handleSave = async (event: FormEvent) => {
        event.preventDefault()

        if (!token) {
            return
        }

        try {
            postState.id.length > 0
                ? putPostsEditorById(token, postState.id, postState)
                : postPostsEditor(token, postState)

            toast.success('Success!')
        } catch (error) {
            toast.error('Failed to submit.')
        }
    }

    return (
        <form onSubmit={handleSave} className="editor-form">
            <div>
                <input
                    type="text"
                    placeholder="title"
                    onChange={handleTitleChange}
                    value={postState.title}
                />
            </div>
            <div>
                <textarea
                    rows={5}
                    placeholder="summary"
                    onChange={handleSummaryChange}
                    value={postState.summary}
                />
            </div>
            <div>
                <textarea
                    rows={30}
                    placeholder="text"
                    onChange={handleTextChange}
                    value={postState.text}
                />
            </div>
            <div className="post-options">
                <input
                    type="checkbox"
                    id="visible"
                    onChange={handleVisibleChange}
                    checked={postState.visible}
                />
                <label htmlFor="visible">Post is visible</label>
            </div>
            <div className="text-center">
                <button type="submit" className="primary-button">
                    Save
                </button>
            </div>
        </form>
    )
}

export default PostEditor
