import { ChangeEvent, FC, FormEvent, useEffect, useRef, useState } from 'react'
import { useAuthContext } from '../../store/auth-context'
import { toast } from 'react-toastify'
import { useParams } from 'react-router-dom'
import { PostEditorDto } from '../../api/codegen'
import { postsApi } from '../../api/api'

const PostEditor: FC = () => {
    const { id } = useParams()
    const { token } = useAuthContext()

    const [postState, setPostState] = useState<PostEditorDto>({
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

        postsApi(token)
            .postsEditorIdGet({ id })
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

    const updateDateRef = useRef<HTMLInputElement>(null)

    const handleSave = async (event: FormEvent) => {
        event.preventDefault()

        if (!token) {
            return
        }

        if (updateDateRef.current && updateDateRef.current.checked) {
            postState.date = new Date().toJSON()
        }

        try {
            postState.id
                ? postsApi(token).postsEditorIdPut({
                    id: postState.id,
                    postEditorDto: postState,
                })
                : postsApi(token).postsEditorPost({ postEditorDto: postState })

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
                <input
                    type="checkbox"
                    id="updateDate"
                    ref={updateDateRef}
                />
                <label htmlFor="updateDate">Update date</label>
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
