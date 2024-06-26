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
 * @interface PostEditorDto
 */
export interface PostEditorDto {
    /**
     * 
     * @type {string}
     * @memberof PostEditorDto
     */
    id?: string;
    /**
     * 
     * @type {string}
     * @memberof PostEditorDto
     */
    title: string;
    /**
     * 
     * @type {string}
     * @memberof PostEditorDto
     */
    summary: string;
    /**
     * 
     * @type {string}
     * @memberof PostEditorDto
     */
    text: string;
    /**
     * 
     * @type {string}
     * @memberof PostEditorDto
     */
    date?: string;
    /**
     * 
     * @type {boolean}
     * @memberof PostEditorDto
     */
    visible: boolean;
}

/**
 * Check if a given object implements the PostEditorDto interface.
 */
export function instanceOfPostEditorDto(value: object): boolean {
    let isInstance = true;
    isInstance = isInstance && "title" in value;
    isInstance = isInstance && "summary" in value;
    isInstance = isInstance && "text" in value;
    isInstance = isInstance && "visible" in value;

    return isInstance;
}

export function PostEditorDtoFromJSON(json: any): PostEditorDto {
    return PostEditorDtoFromJSONTyped(json, false);
}

export function PostEditorDtoFromJSONTyped(json: any, ignoreDiscriminator: boolean): PostEditorDto {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'id': !exists(json, 'id') ? undefined : json['id'],
        'title': json['title'],
        'summary': json['summary'],
        'text': json['text'],
        'date': !exists(json, 'date') ? undefined : json['date'],
        'visible': json['visible'],
    };
}

export function PostEditorDtoToJSON(value?: PostEditorDto | null): any {
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

