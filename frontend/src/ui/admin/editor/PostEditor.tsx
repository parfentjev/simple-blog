import { FC, FormEvent, useEffect, useState } from 'react'
import { useAuthContext } from '../../../store/auth-context'
import { toast } from 'react-toastify'
import { useParams } from 'react-router-dom'
import { PostEditorDto } from '../../../api/codegen'
import { postsApi } from '../../../api/api'
import MediaUploadingForm from '../media/MediaUploadingForm'

const PostEditor: FC = () => {
    const { id } = useParams()
    const { token } = useAuthContext()

    const [postState, setPostState] = useState<PostEditorDto>({
        id: '',
        title: '',
        summary: '',
        text: '',
        date: new Date(),
        visible: false,
    })

    const [updateDate, setUpdateDate] = useState(false)

    useEffect(() => {
        if (!id || !token) {
            return
        }

        postsApi(token)
            .postsEditorIdGet({ id })
            .then((post) => setPostState(post))
            .catch(() => toast.error('Failed to load post.'))
    }, [id, token])

    const handleSave = async (event: FormEvent) => {
        event.preventDefault()

        if (!token) {
            toast.error('Not authenticated.')
        }

        if (updateDate) {
            postState.date = new Date()
        }

        try {
            postState.id
                ? postsApi(token).postsEditorIdPut({
                      id: postState.id,
                      postEditorDto: postState,
                  })
                : postsApi(token).postsEditorPost({ postEditorDto: postState })

            toast.success('Saved!')
        } catch (error) {
            toast.error('Failed to submit.')
        }
    }

    const [deleteCounter, setDeleteCounter] = useState(0)

    useEffect(() => {
        if (deleteCounter < 3 || !postState.id) {
            return
        }

        postsApi(token)
            .postsEditorIdDelete({ id: postState.id })
            .then(() => toast.success('Deleted.'))
            .catch(() => toast.error('Failed to delete.'))
    }, [deleteCounter, postState, token])

    return (
        <form onSubmit={handleSave} className="editor-form">
            <div>
                <input
                    type="text"
                    placeholder="title"
                    onChange={(e) =>
                        setPostState((postDto) => {
                            return { ...postDto, title: e.target.value }
                        })
                    }
                    value={postState.title}
                />
            </div>
            <div>
                <textarea
                    rows={5}
                    placeholder="summary"
                    onChange={(e) =>
                        setPostState((postDto) => {
                            return { ...postDto, summary: e.target.value }
                        })
                    }
                    value={postState.summary}
                />
            </div>
            <div>
                <textarea
                    rows={30}
                    placeholder="text"
                    onChange={(e) =>
                        setPostState((postDto) => {
                            return { ...postDto, text: e.target.value }
                        })
                    }
                    value={postState.text}
                />
            </div>
            <MediaUploadingForm />
            <div className="post-options">
                <input
                    type="checkbox"
                    id="visible"
                    onChange={(e) => {
                        setPostState((postDto) => {
                            return { ...postDto, visible: e.target.checked }
                        })
                    }}
                    checked={postState.visible}
                />
                <label htmlFor="visible">Post is visible</label>
                <input
                    type="checkbox"
                    id="updateDate"
                    onChange={(e) => setUpdateDate((update) => !update)}
                />
                <label htmlFor="updateDate">Update date</label>
            </div>
            <div className="text-center">
                <button type="submit" className="primary-button">
                    Save
                </button>
                <button
                    type="button"
                    className="secondary-button"
                    onClick={() => {
                        setDeleteCounter((counter) => ++counter)
                        setTimeout(() => setDeleteCounter(0), 1000)
                    }}
                    title="Click 3 times to confirm."
                >
                    Delete
                </button>
            </div>
        </form>
    )
}

export default PostEditor
