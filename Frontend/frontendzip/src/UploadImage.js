import React from "react";
import { useState } from "react";
import axios from 'axios'

const Upload = ()=>{
    const [image, setImage] = useState(null);
    const [imageData, setImageData] = useState(null);

    const handleImageFile = (e) =>{
        console.log(e.target.files);
        setImage(e.target.files[0]);
        
    };

    const handleUpload = async ()=>{
        console.log(image);
        const formData = new FormData();
        formData.append('image', image);
        console.log(formData.getAll('image'));
        try{
            const response = await axios.post('http://localhost:8080/api/upload', formData);
            setImageData(response.data);
        }catch(error){
            console.log('Error uploading image:', error);
        }
    }

    return (
        <div className="container">
            <input type = "file" onChange={handleImageFile}/>
            <button onClick={handleUpload}>Upload Image</button>
            {imageData && (
                <div>
                    <img src = {`imageData:image/jpeg;base64, ${imageData.imageBase64}`} alt = "Uploaded"/>
                    <p>{imageData.extractedText}</p>
                    <p>Bold Words: {imageData.boldWords}</p>
                </div>
            )}
        </div>
    )
};

export default Upload;


