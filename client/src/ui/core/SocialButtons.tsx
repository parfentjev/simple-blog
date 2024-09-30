import { ChangeEvent, FC, useEffect, useState } from 'react'
import ModalDialog from './ModalDialog'
import MastodonClient from '../../api/mastodon/client'
import { toast } from 'react-toastify'

const mastodonUrlKey = 'mastodonUrl'

const SocialButtons: FC = () => {
    const [modalVisible, setModalVisible] = useState(false)
    const [mastodonUrl, setMastodonUrl] = useState('')

    const toggleModal = () => {
        setModalVisible((s) => !s)
    }

    const shareHandler = async () => {
        const exists = await new MastodonClient(mastodonUrl).getInstance()

        if (!exists) {
            toast.error(`${mastodonUrl} was not found, is the url correct?`)
            return
        }

        localStorage.setItem(mastodonUrlKey, mastodonUrl)
        const text = `${document.title} ${document.URL}`
        window.open(`${mastodonUrl}/share?text=${text}`, '_blank')
    }

    const handleMastodonUrlChange = (event: ChangeEvent<HTMLInputElement>) => {
        setMastodonUrl(event.target.value)
    }

    useEffect(() => {
        setMastodonUrl(
            localStorage.getItem(mastodonUrlKey) || 'https://mastodon.ie/'
        )
    }, [])

    return (
        <>
            <ModalDialog
                visible={modalVisible}
                cancelFn={toggleModal}
                submitFn={shareHandler}
            >
                <p>Share this post on your Mastodon instance:</p>
                <p>
                    <input
                        type="text"
                        value={mastodonUrl}
                        onChange={handleMastodonUrlChange}
                    />
                </p>
            </ModalDialog>
            <div className="social-buttons text-center">
                <img
                    className="social-button"
                    src={`/images/mastodon-logo.png`}
                    alt="Share on Mastodon"
                    title="Share on Mastodon"
                    onClick={toggleModal}
                />
            </div>
        </>
    )
}

export default SocialButtons
