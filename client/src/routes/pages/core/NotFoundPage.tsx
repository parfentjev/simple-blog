import { FC } from 'react'

const NotFoundPage: FC = () => {
    return (
        <>
            <h1>Page does not exist</h1>
            <p>
                Greetings, dear visitor! Unfortunately, it seems this page
                doesn't exist.
            </p>
            <div className="text-center">
                <img
                    alt="A puzzled robot"
                    src={`${process.env.PUBLIC_URL}/images/404.png`}
                />
            </div>
        </>
    )
}

export default NotFoundPage
