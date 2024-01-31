/* tslint:disable */
/* eslint-disable */
/**
 * Simple Blog
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


import * as runtime from '../runtime';
import type {
  UserTokenDto,
  UsersPostRequest,
} from '../models/index';
import {
    UserTokenDtoFromJSON,
    UserTokenDtoToJSON,
    UsersPostRequestFromJSON,
    UsersPostRequestToJSON,
} from '../models/index';

export interface UsersPostOperationRequest {
    usersPostRequest: UsersPostRequest;
}

export interface UsersTokenPostRequest {
    usersPostRequest: UsersPostRequest;
}

/**
 * 
 */
export class UsersApi extends runtime.BaseAPI {

    /**
     * Create a new user
     */
    async usersPostRaw(requestParameters: UsersPostOperationRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<void>> {
        if (requestParameters.usersPostRequest === null || requestParameters.usersPostRequest === undefined) {
            throw new runtime.RequiredError('usersPostRequest','Required parameter requestParameters.usersPostRequest was null or undefined when calling usersPost.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/users`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: UsersPostRequestToJSON(requestParameters.usersPostRequest),
        }, initOverrides);

        return new runtime.VoidApiResponse(response);
    }

    /**
     * Create a new user
     */
    async usersPost(requestParameters: UsersPostOperationRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<void> {
        await this.usersPostRaw(requestParameters, initOverrides);
    }

    /**
     * Generate a user token
     */
    async usersTokenPostRaw(requestParameters: UsersTokenPostRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<UserTokenDto>> {
        if (requestParameters.usersPostRequest === null || requestParameters.usersPostRequest === undefined) {
            throw new runtime.RequiredError('usersPostRequest','Required parameter requestParameters.usersPostRequest was null or undefined when calling usersTokenPost.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/users/token`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: UsersPostRequestToJSON(requestParameters.usersPostRequest),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => UserTokenDtoFromJSON(jsonValue));
    }

    /**
     * Generate a user token
     */
    async usersTokenPost(requestParameters: UsersTokenPostRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<UserTokenDto> {
        const response = await this.usersTokenPostRaw(requestParameters, initOverrides);
        return await response.value();
    }

}