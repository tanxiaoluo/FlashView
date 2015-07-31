/** 
 * @项目名称：FlashViewDemo   
 * @文件名：BaseModel.java    
 * @版本信息：
 * @日期：2015年7月30日    
 * @Copyright 2015 www.517na.com Inc. All rights reserved.         
 */
package com.example.flashviewdemo.model;

import java.io.Serializable;

/**    
 *     
 * @项目名称：FlashViewDemo    
 * @类名称：BaseModel    
 * @类描述：    
 * @创建人：zhaonan    
 * @创建时间：2015年7月30日 下午2:07:32    
 * @修改人：zhaonan    
 * @修改时间：2015年7月30日 下午2:07:32    
 * @修改备注：    
 * @version     
 *     
 */
public class BaseModel implements Serializable{

    private static final long serialVersionUID = 1L;
    
    /**图片链接 */
    public String mImageUrl;
    
    /**图片描述*/
    public String mDes;
    
    /**图片id*/
    public int mImageId;
    
}
