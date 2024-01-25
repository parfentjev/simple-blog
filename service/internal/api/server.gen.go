// Package api provides primitives to interact with the openapi HTTP API.
//
// Code generated by github.com/deepmap/oapi-codegen/v2 version v2.0.0 DO NOT EDIT.
package api

import (
	"bytes"
	"compress/gzip"
	"encoding/base64"
	"fmt"
	"net/http"
	"net/url"
	"path"
	"strings"

	"github.com/getkin/kin-openapi/openapi3"
	"github.com/gin-gonic/gin"
	"github.com/oapi-codegen/runtime"
)

const (
	BearerAuthScopes = "bearerAuth.Scopes"
)

// PostDto defines model for PostDto.
type PostDto struct {
	Date    string `json:"date"`
	Id      string `json:"id"`
	Summary string `json:"summary"`
	Text    string `json:"text"`
	Title   string `json:"title"`
	Visible bool   `json:"visible"`
}

// PostEditorDto defines model for PostEditorDto.
type PostEditorDto struct {
	Date    *string `json:"date,omitempty"`
	Id      *string `json:"id,omitempty"`
	Summary string  `json:"summary"`
	Text    string  `json:"text"`
	Title   string  `json:"title"`
	Visible bool    `json:"visible"`
}

// PostPreviewDto defines model for PostPreviewDto.
type PostPreviewDto struct {
	Date    string `json:"date"`
	Id      string `json:"id"`
	Summary string `json:"summary"`
	Title   string `json:"title"`
	Visible bool   `json:"visible"`
}

// UserTokenDto defines model for UserTokenDto.
type UserTokenDto struct {
	Expires int    `json:"expires"`
	Token   string `json:"token"`
}

// PostUsersJSONBody defines parameters for PostUsers.
type PostUsersJSONBody struct {
	Password string `json:"password"`
	Username string `json:"username"`
}

// PostUsersTokenJSONBody defines parameters for PostUsersToken.
type PostUsersTokenJSONBody struct {
	Password string `json:"password"`
	Username string `json:"username"`
}

// PostPostsEditorJSONRequestBody defines body for PostPostsEditor for application/json ContentType.
type PostPostsEditorJSONRequestBody = PostEditorDto

// PutPostsEditorIdJSONRequestBody defines body for PutPostsEditorId for application/json ContentType.
type PutPostsEditorIdJSONRequestBody = PostEditorDto

// PostUsersJSONRequestBody defines body for PostUsers for application/json ContentType.
type PostUsersJSONRequestBody PostUsersJSONBody

// PostUsersTokenJSONRequestBody defines body for PostUsersToken for application/json ContentType.
type PostUsersTokenJSONRequestBody PostUsersTokenJSONBody

// ServerInterface represents all server handlers.
type ServerInterface interface {

	// (GET /posts/editor)
	GetPostsEditor(c *gin.Context)

	// (POST /posts/editor)
	PostPostsEditor(c *gin.Context)

	// (DELETE /posts/editor/{id})
	DeletePostsEditorId(c *gin.Context, id string)

	// (GET /posts/editor/{id})
	GetPostsEditorId(c *gin.Context, id string)

	// (PUT /posts/editor/{id})
	PutPostsEditorId(c *gin.Context, id string)

	// (GET /posts/published)
	GetPostsPublished(c *gin.Context)

	// (GET /posts/published/{id})
	GetPostsPublishedId(c *gin.Context, id string)

	// (GET /rss/posts)
	GetRssPosts(c *gin.Context)

	// (POST /users)
	PostUsers(c *gin.Context)

	// (POST /users/token)
	PostUsersToken(c *gin.Context)
}

// ServerInterfaceWrapper converts contexts to parameters.
type ServerInterfaceWrapper struct {
	Handler            ServerInterface
	HandlerMiddlewares []MiddlewareFunc
	ErrorHandler       func(*gin.Context, error, int)
}

type MiddlewareFunc func(c *gin.Context)

// GetPostsEditor operation middleware
func (siw *ServerInterfaceWrapper) GetPostsEditor(c *gin.Context) {

	c.Set(BearerAuthScopes, []string{})

	for _, middleware := range siw.HandlerMiddlewares {
		middleware(c)
		if c.IsAborted() {
			return
		}
	}

	siw.Handler.GetPostsEditor(c)
}

// PostPostsEditor operation middleware
func (siw *ServerInterfaceWrapper) PostPostsEditor(c *gin.Context) {

	c.Set(BearerAuthScopes, []string{})

	for _, middleware := range siw.HandlerMiddlewares {
		middleware(c)
		if c.IsAborted() {
			return
		}
	}

	siw.Handler.PostPostsEditor(c)
}

// DeletePostsEditorId operation middleware
func (siw *ServerInterfaceWrapper) DeletePostsEditorId(c *gin.Context) {

	var err error

	// ------------- Path parameter "id" -------------
	var id string

	err = runtime.BindStyledParameter("simple", false, "id", c.Param("id"), &id)
	if err != nil {
		siw.ErrorHandler(c, fmt.Errorf("Invalid format for parameter id: %w", err), http.StatusBadRequest)
		return
	}

	c.Set(BearerAuthScopes, []string{})

	for _, middleware := range siw.HandlerMiddlewares {
		middleware(c)
		if c.IsAborted() {
			return
		}
	}

	siw.Handler.DeletePostsEditorId(c, id)
}

// GetPostsEditorId operation middleware
func (siw *ServerInterfaceWrapper) GetPostsEditorId(c *gin.Context) {

	var err error

	// ------------- Path parameter "id" -------------
	var id string

	err = runtime.BindStyledParameter("simple", false, "id", c.Param("id"), &id)
	if err != nil {
		siw.ErrorHandler(c, fmt.Errorf("Invalid format for parameter id: %w", err), http.StatusBadRequest)
		return
	}

	c.Set(BearerAuthScopes, []string{})

	for _, middleware := range siw.HandlerMiddlewares {
		middleware(c)
		if c.IsAborted() {
			return
		}
	}

	siw.Handler.GetPostsEditorId(c, id)
}

// PutPostsEditorId operation middleware
func (siw *ServerInterfaceWrapper) PutPostsEditorId(c *gin.Context) {

	var err error

	// ------------- Path parameter "id" -------------
	var id string

	err = runtime.BindStyledParameter("simple", false, "id", c.Param("id"), &id)
	if err != nil {
		siw.ErrorHandler(c, fmt.Errorf("Invalid format for parameter id: %w", err), http.StatusBadRequest)
		return
	}

	c.Set(BearerAuthScopes, []string{})

	for _, middleware := range siw.HandlerMiddlewares {
		middleware(c)
		if c.IsAborted() {
			return
		}
	}

	siw.Handler.PutPostsEditorId(c, id)
}

// GetPostsPublished operation middleware
func (siw *ServerInterfaceWrapper) GetPostsPublished(c *gin.Context) {

	for _, middleware := range siw.HandlerMiddlewares {
		middleware(c)
		if c.IsAborted() {
			return
		}
	}

	siw.Handler.GetPostsPublished(c)
}

// GetPostsPublishedId operation middleware
func (siw *ServerInterfaceWrapper) GetPostsPublishedId(c *gin.Context) {

	var err error

	// ------------- Path parameter "id" -------------
	var id string

	err = runtime.BindStyledParameter("simple", false, "id", c.Param("id"), &id)
	if err != nil {
		siw.ErrorHandler(c, fmt.Errorf("Invalid format for parameter id: %w", err), http.StatusBadRequest)
		return
	}

	for _, middleware := range siw.HandlerMiddlewares {
		middleware(c)
		if c.IsAborted() {
			return
		}
	}

	siw.Handler.GetPostsPublishedId(c, id)
}

// GetRssPosts operation middleware
func (siw *ServerInterfaceWrapper) GetRssPosts(c *gin.Context) {

	for _, middleware := range siw.HandlerMiddlewares {
		middleware(c)
		if c.IsAborted() {
			return
		}
	}

	siw.Handler.GetRssPosts(c)
}

// PostUsers operation middleware
func (siw *ServerInterfaceWrapper) PostUsers(c *gin.Context) {

	for _, middleware := range siw.HandlerMiddlewares {
		middleware(c)
		if c.IsAborted() {
			return
		}
	}

	siw.Handler.PostUsers(c)
}

// PostUsersToken operation middleware
func (siw *ServerInterfaceWrapper) PostUsersToken(c *gin.Context) {

	for _, middleware := range siw.HandlerMiddlewares {
		middleware(c)
		if c.IsAborted() {
			return
		}
	}

	siw.Handler.PostUsersToken(c)
}

// GinServerOptions provides options for the Gin server.
type GinServerOptions struct {
	BaseURL      string
	Middlewares  []MiddlewareFunc
	ErrorHandler func(*gin.Context, error, int)
}

// RegisterHandlers creates http.Handler with routing matching OpenAPI spec.
func RegisterHandlers(router gin.IRouter, si ServerInterface) {
	RegisterHandlersWithOptions(router, si, GinServerOptions{})
}

// RegisterHandlersWithOptions creates http.Handler with additional options
func RegisterHandlersWithOptions(router gin.IRouter, si ServerInterface, options GinServerOptions) {
	errorHandler := options.ErrorHandler
	if errorHandler == nil {
		errorHandler = func(c *gin.Context, err error, statusCode int) {
			c.JSON(statusCode, gin.H{"msg": err.Error()})
		}
	}

	wrapper := ServerInterfaceWrapper{
		Handler:            si,
		HandlerMiddlewares: options.Middlewares,
		ErrorHandler:       errorHandler,
	}

	router.GET(options.BaseURL+"/posts/editor", wrapper.GetPostsEditor)
	router.POST(options.BaseURL+"/posts/editor", wrapper.PostPostsEditor)
	router.DELETE(options.BaseURL+"/posts/editor/:id", wrapper.DeletePostsEditorId)
	router.GET(options.BaseURL+"/posts/editor/:id", wrapper.GetPostsEditorId)
	router.PUT(options.BaseURL+"/posts/editor/:id", wrapper.PutPostsEditorId)
	router.GET(options.BaseURL+"/posts/published", wrapper.GetPostsPublished)
	router.GET(options.BaseURL+"/posts/published/:id", wrapper.GetPostsPublishedId)
	router.GET(options.BaseURL+"/rss/posts", wrapper.GetRssPosts)
	router.POST(options.BaseURL+"/users", wrapper.PostUsers)
	router.POST(options.BaseURL+"/users/token", wrapper.PostUsersToken)
}

// Base64 encoded, gzipped, json marshaled Swagger object
var swaggerSpec = []string{

	"H4sIAAAAAAAC/+RYS2/jNhD+KwRboIcKlrebAgvdmm67SHuokQd6CHygpbHFXZnkckZxXEP/vRhSsS1b",
	"TrJ5tO72ZFkczuP7Zj6TXsnczp01YAhltpKYlzBX4XFkkd6T5UfnrQNPGsJCoQj4k5YOZCaRvDYz2SRS",
	"F72vsZ7PlV/2rhHcUv+Cpqo/yo1GPemsTaytQBnZNIn08LnWHgqZXXM+d442WbQxk1jGxt04uXNnJx8h",
	"Jw7FEPxSaLL+vw3EQQweKn7k4UbD4vWrfz22H0HzFYK/tJ/A9NYJt077+Nju1IZgBj4kztt6Et8lIJgl",
	"a1/7WTBAkNde0/KCZzAGnIDy4H+qqdx8+9X6uSKZyd/+vORKgzXjElbl2nNJ5GTDjrWZhrpalOWFnrsK",
	"xGllZ4wMeNTWyEy+GQy5JuvAKKdlJt8OhoOhTKRTVIZ8UmeRMIUwE/xiBqFrC8Dca0fRzwcgoapKBONE",
	"TCyVwtWTSmMJhVCmEIVXU0IZYnnF286KuJG7DuPMScYQnTUYwfhhOOSP3BoCE8Iq5yqdh/3pR7Rmo2D8",
	"9K2HqczkN+lG4tJW39Kd5g4wdYv443fG4mT4Zr++K6NqKq3Xf0HBRj/GxLpGZ4bAG1UJBH8DXoD31nd4",
	"ltl1l+HrccONoWbIPROQkOMmkYzjfoCfPSgCoYSBRYB6D85Q5A6en2tAOrXF8kWh3Ihk02198jU0ezz2",
	"YHo0cDdJt8vTlS6aGK+CqHjdyO/De6GMgFuNpM2sn41ot8XHWRFGy6s5EHgM+XVds7EIuqb5K4+hTKRR",
	"Yd7D+y7SyRZlu4I07p+mJ7NwMjzZNwoJG0tiamvzj8zGYQEKLCRiUQKV4IWm73BLhaznPB9QoKNg6MWG",
	"9PlCdyScu7qH8ytXqMeM4aj+lxk+CgX+CmZ/I9Trqb73RLKZ/bDr4OiP1t6O4PzxhRA+AqP179l9ytmB",
	"SkyWcQAewOt/pZavMQB97HnEyOD9lBlxfnEhpgCFWOjOcftgs58jjtq1LwDSI35/O6+6WO5y83rNXGPo",
	"rNVjzsRs23smvgpOnq7F3fuhU4gL6/svvZxD7O+Hrohry2Tjseea+LTTNVcs8gBOK+A9TJyqQrSQiAlj",
	"Egzf7huew0wjRVCFRlFoVJMKntPykZItjtP1zbqf6Q9gmFbmmu3F3Q37ANuX7fJXSvnLSV/nv5CeSQ5r",
	"T2ql53ZGOCGwUfxlqX3V/smRpWllc1WVFil7N3w3lE2yvY5ZmvJOncNgqj6BqxSSzskD4ABAbh0uVnc/",
	"RjEq+2lfRBFqxs3fAQAA//9vz0FHMhUAAA==",
}

// GetSwagger returns the content of the embedded swagger specification file
// or error if failed to decode
func decodeSpec() ([]byte, error) {
	zipped, err := base64.StdEncoding.DecodeString(strings.Join(swaggerSpec, ""))
	if err != nil {
		return nil, fmt.Errorf("error base64 decoding spec: %w", err)
	}
	zr, err := gzip.NewReader(bytes.NewReader(zipped))
	if err != nil {
		return nil, fmt.Errorf("error decompressing spec: %w", err)
	}
	var buf bytes.Buffer
	_, err = buf.ReadFrom(zr)
	if err != nil {
		return nil, fmt.Errorf("error decompressing spec: %w", err)
	}

	return buf.Bytes(), nil
}

var rawSpec = decodeSpecCached()

// a naive cached of a decoded swagger spec
func decodeSpecCached() func() ([]byte, error) {
	data, err := decodeSpec()
	return func() ([]byte, error) {
		return data, err
	}
}

// Constructs a synthetic filesystem for resolving external references when loading openapi specifications.
func PathToRawSpec(pathToFile string) map[string]func() ([]byte, error) {
	res := make(map[string]func() ([]byte, error))
	if len(pathToFile) > 0 {
		res[pathToFile] = rawSpec
	}

	return res
}

// GetSwagger returns the Swagger specification corresponding to the generated code
// in this file. The external references of Swagger specification are resolved.
// The logic of resolving external references is tightly connected to "import-mapping" feature.
// Externally referenced files must be embedded in the corresponding golang packages.
// Urls can be supported but this task was out of the scope.
func GetSwagger() (swagger *openapi3.T, err error) {
	resolvePath := PathToRawSpec("")

	loader := openapi3.NewLoader()
	loader.IsExternalRefsAllowed = true
	loader.ReadFromURIFunc = func(loader *openapi3.Loader, url *url.URL) ([]byte, error) {
		pathToFile := url.String()
		pathToFile = path.Clean(pathToFile)
		getSpec, ok := resolvePath[pathToFile]
		if !ok {
			err1 := fmt.Errorf("path not found: %s", pathToFile)
			return nil, err1
		}
		return getSpec()
	}
	var specData []byte
	specData, err = rawSpec()
	if err != nil {
		return
	}
	swagger, err = loader.LoadFromData(specData)
	if err != nil {
		return
	}
	return
}
