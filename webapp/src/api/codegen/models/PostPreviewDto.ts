/* tslint:disable */
/* eslint-disable */
/**
 * Simple Blog
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.2
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
 * @interface PostPreviewDto
 */
export interface PostPreviewDto {
    /**
     * 
     * @type {string}
     * @memberof PostPreviewDto
     */
    id: string;
    /**
     * 
     * @type {string}
     * @memberof PostPreviewDto
     */
    title: string;
    /**
     * 
     * @type {string}
     * @memberof PostPreviewDto
     */
    summary: string;
    /**
     * 
     * @type {string}
     * @memberof PostPreviewDto
     */
    date: string;
    /**
     * 
     * @type {boolean}
     * @memberof PostPreviewDto
     */
    visible: boolean;
}

/**
 * Check if a given object implements the PostPreviewDto interface.
 */
export function instanceOfPostPreviewDto(value: object): boolean {
    let isInstance = true;
    isInstance = isInstance && "id" in value;
    isInstance = isInstance && "title" in value;
    isInstance = isInstance && "summary" in value;
    isInstance = isInstance && "date" in value;
    isInstance = isInstance && "visible" in value;

    return isInstance;
}

export function PostPreviewDtoFromJSON(json: any): PostPreviewDto {
    return PostPreviewDtoFromJSONTyped(json, false);
}

export function PostPreviewDtoFromJSONTyped(json: any, ignoreDiscriminator: boolean): PostPreviewDto {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'id': json['id'],
        'title': json['title'],
        'summary': json['summary'],
        'date': json['date'],
        'visible': json['visible'],
    };
}

export function PostPreviewDtoToJSON(value?: PostPreviewDto | null): any {
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
        'date': value.date,
        'visible': value.visible,
    };
}

