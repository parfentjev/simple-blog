import { FC } from 'react'

const BannerPage: FC = () => {
    const bannerCode =
        '<a href="https://fakeplastictrees.ee/" target="_blank"><img src="https://fakeplastictrees.ee/images/retro/banner.png" alt="fakeplastictrees.ee banner"/></a>'
    return (
        <>
            <p>Hi! Please share my awesome banner on your website!</p>
            <p>
                <code>{bannerCode}</code>
            </p>
        </>
    )
}

export default BannerPage
