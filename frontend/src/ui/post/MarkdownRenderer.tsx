import { FC } from 'react'
import { ReactMarkdown } from 'react-markdown/lib/react-markdown'
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter'
import { tomorrow } from 'react-syntax-highlighter/dist/cjs/styles/prism'

const MarkdownRenderer: FC<{ text: string }> = ({ text }) => {
  return (
    <>
      <ReactMarkdown
        linkTarget={'_blank'}
        components={{
          code(props) {
            const { children, className, node, inline, ...rest } = props
            const match = /language-(\w+)/.exec(className || '')

            return inline ? (
              <code>{children}</code>
            ) : (
              <SyntaxHighlighter
                {...rest}
                PreTag='div'
                children={String(children).replace(/\n$/, '')}
                language={match != null ? match[1] : ''}
                style={tomorrow}
              />
            )
          },
        }}
      >
        {text}
      </ReactMarkdown>
    </>
  )
}

export default MarkdownRenderer
