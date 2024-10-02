import { FC, ReactNode } from 'react'
import { ReactMarkdown } from 'react-markdown/lib/react-markdown'
import { Link } from 'react-router-dom'
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter'
import { tomorrow } from 'react-syntax-highlighter/dist/cjs/styles/prism'

const MarkdownRenderer: FC<{ children: ReactNode }> = ({ children }) => {
    return (
        <>
            <ReactMarkdown
                linkTarget={'_blank'}
                components={{
                    img(props) {
                        const { src, alt } = props
                        const image = <img src={src} alt={alt} title={alt} />

                        return (
                            (src && (
                                <Link to={src} target="_blank" rel="noreferrer">
                                    {image}
                                </Link>
                            )) ||
                            image
                        )
                    },
                    code(props) {
                        const { children, className, node, inline, ...rest } =
                            props
                        const match = /language-(\w+)/.exec(className || '')

                        return inline ? (
                            <code>{children}</code>
                        ) : (
                            <SyntaxHighlighter
                                {...rest}
                                PreTag="div"
                                language={match != null ? match[1] : ''}
                                style={tomorrow}
                            >
                                {String(children).replace(/\n$/, '')}
                            </SyntaxHighlighter>
                        )
                    },
                }}
            >
                {String(children)}
            </ReactMarkdown>
        </>
    )
}

export default MarkdownRenderer
