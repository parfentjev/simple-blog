import { FC, ReactNode } from 'react'
import Markdown from 'react-markdown'
import { Link } from 'react-router'
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter'
import { tomorrow as theme } from 'react-syntax-highlighter/dist/cjs/styles/prism'

const MarkdownRenderer: FC<{ children: ReactNode }> = ({ children }) => {
    return (
        <>
            <Markdown
                components={{
                    img(props) {
                        const { src, alt } = props
                        const image = <img src={src} alt={alt} title={alt} />

                        return (
                            (src && (
                                <Link to={src} target="_blank">
                                    {image}
                                </Link>
                            )) ||
                            image
                        )
                    },
                    a(props) {
                        return (
                            <Link to={props.href || '#'} target="_blank">
                                {props.children}
                            </Link>
                        )
                    },
                    code(props) {
                        const { children, className } = props
                        const match = /language-(\w+)/.exec(className || '')

                        return match ? (
                            <SyntaxHighlighter
                                PreTag="div"
                                language={match != null ? match[1] : ''}
                                style={theme}
                            >
                                {String(children).replace(/\n$/, '')}
                            </SyntaxHighlighter>
                        ) : (
                            <code className={className}>{children}</code>
                        )
                    },
                }}
            >
                {String(children)}
            </Markdown>
        </>
    )
}

export default MarkdownRenderer
