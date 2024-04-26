/* tslint:disable */
/* eslint-disable */
/**
 * Simple Blog
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { exists, mapValues } from '../runtime';
/**
 * 
 * @export
 * @interface PostDto
 */
export interface PostDto {
    /**
     * 
     * @type {string}
     * @memberof PostDto
     */
    id: string;
    /**
     * 
     * @type {string}
     * @memberof PostDto
     */
    title: string;
    /**
     * 
     * @type {string}
     * @memberof PostDto
     */
    summary: string;
    /**
     * 
     * @type {string}
     * @memberof PostDto
     */
    text: string;
    /**
     * 
     * @type {string}
     * @memberof PostDto
     */
    date: string;
    /**
     * 
     * @type {boolean}
     * @memberof PostDto
     */
    visible: boolean;
}

/**
 * Check if a given object implements the PostDto interface.
 */
export function instanceOfPostDto(value: object): boolean {
    let isInstance = true;
    isInstance = isInstance && "id" in value;
    isInstance = isInstance && "title" in value;
    isInstance = isInstance && "summary" in value;
    isInstance = isInstance && "text" in value;
    isInstance = isInstance && "date" in value;
    isInstance = isInstance && "visible" in value;

    return isInstance;
}

export function PostDtoFromJSON(json: any): PostDto {
    return PostDtoFromJSONTyped(json, false);
}

export function PostDtoFromJSONTyped(json: any, ignoreDiscriminator: boolean): PostDto {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'id': json['id'],
        'title': json['title'],
        'summary': json['summary'],
        'text': json['text'],
        'date': json['date'],
        'visible': json['visible'],
    };
}

export function PostDtoToJSON(value?: PostDto | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'id': value.id,
        'title': value.title,
        'summary': value.summary,
        'text': value.text,
        'date': value.date,
        'visible': value.visible,
    };
}

